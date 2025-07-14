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

class CounselFragment : Fragment() {

    private var _binding: FragmentCounselBinding? = null
    private val binding get() = _binding!!

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
                    val cityName = adminArea
                        .replace("광역시", "")
                        .replace("특별시", "")
                        .replace("특별자치시", "")
                        .replace("특별자치도", "")
                        .replace("자치시", "")
                        .replace("자치도", "")
                        .replace("도", "")
                        .trim()
                    binding.tvCounselTitle.text = "주변 상담소 ($cityName)"
                }
            } else {
                binding.tvCounselTitle.text = "위치 정보를 가져올 수 없습니다"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 