package jin.contest.ta_android.ui.counsel

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import jin.contest.ta_android.databinding.FragmentCounselBinding
import java.util.*
import androidx.lifecycle.lifecycleScope
import jin.contest.ta_android.data.model.CounselItem
import jin.contest.ta_android.data.remote.RetrofitClient
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounselFragment : Fragment() {

    private var _binding: FragmentCounselBinding? = null
    private val binding get() = _binding!!

    private var cityName: String? = null // 도시명 저장용

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCounselBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        } else {
            showCurrentDong()
        }

        val mapFragment = childFragmentManager.findFragmentById(jin.contest.ta_android.R.id.map_container) as? com.google.android.gms.maps.SupportMapFragment
            ?: com.google.android.gms.maps.SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(jin.contest.ta_android.R.id.map_container, it)
                    .commitNow()
            }

        mapFragment.getMapAsync { googleMap ->
            googleMap.uiSettings.isZoomControlsEnabled = true

            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                googleMap.isMyLocationEnabled = true

                val fusedLocationClient = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = com.google.android.gms.maps.model.LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    }
                }
            }

            // cityName이 null이면 상담소 리스트/마커 추가 로직 실행 안 함
            if (cityName == null) return@getMapAsync

            // 상담소 리스트 받아와서 지도에 마커 추가
            lifecycleScope.launch {
                try {
                    val city = cityName!!
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.apiService.getCounselList(city, "")
                    }
                    if (response.isSuccessful) {
                        val counselList = response.body()?.data ?: emptyList()
                        for (counsel in counselList) {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val addressList = withContext(Dispatchers.IO) {
                                geocoder.getFromLocationName(counsel.주소, 1)
                            }
                            if (!addressList.isNullOrEmpty()) {
                                val lat = addressList[0].latitude
                                val lng = addressList[0].longitude
                                val markerOptions = MarkerOptions()
                                    .position(LatLng(lat, lng))
                                    .title(counsel.기관명)
                                googleMap.addMarker(markerOptions)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showCurrentDong()
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun showCurrentDong() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val adminArea = addresses[0].adminArea ?: "알 수 없음"
                    val city = adminArea
                        .replace("광역시", "")
                        .replace("특별시", "")
                        .replace("특별자치시", "")
                        .replace("특별자치도", "")
                        .replace("자치시", "")
                        .replace("자치도", "")
                        .replace("도", "")
                        .trim()
                    cityName = city // 도시명 저장
                    binding.tvCounselTitle.text = "주변 상담소 ($city)"
                    // cityName이 세팅된 후에만 상담소 리스트/마커 추가 로직 실행
                    addCounselMarkers(city)
                }
            } else {
                binding.tvCounselTitle.text = "위치 정보를 가져올 수 없습니다"
            }
        }
    }

    // cityName이 세팅된 후에만 실행되는 함수
    private fun addCounselMarkers(city: String) {
        val mapFragment = childFragmentManager.findFragmentById(jin.contest.ta_android.R.id.map_container) as? com.google.android.gms.maps.SupportMapFragment
            ?: return
        mapFragment.getMapAsync { googleMap ->
            lifecycleScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitClient.apiService.getCounselList(city, "")
                    }
                    if (response.isSuccessful) {
                        val counselList = response.body()?.data ?: emptyList()
                        for (counsel in counselList) {
                            val geocoder = Geocoder(requireContext(), Locale.getDefault())
                            val addressList = withContext(Dispatchers.IO) {
                                geocoder.getFromLocationName(counsel.주소, 1)
                            }
                            if (!addressList.isNullOrEmpty()) {
                                val lat = addressList[0].latitude
                                val lng = addressList[0].longitude
                                val markerOptions = MarkerOptions()
                                    .position(LatLng(lat, lng))
                                    .title(counsel.기관명)
                                googleMap.addMarker(markerOptions)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 