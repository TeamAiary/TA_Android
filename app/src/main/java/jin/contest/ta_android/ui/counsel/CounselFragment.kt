package jin.contest.ta_android.ui.counsel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jin.contest.ta_android.databinding.FragmentCounselBinding
import jin.contest.ta_android.R

class CounselFragment : Fragment() {

    private var _binding: FragmentCounselBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCounselBinding.inflate(inflater, container, false)
        val root = binding.root

        // TODO: 구글맵 API 연동 및 주변 상담소 검색 기능 구현
        // 1. 현재 위치 가져오기
        // 2. Places API로 상담소 검색
        // 3. 지도에 마커 표시
        // 4. 상담소 목록 표시

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SupportMapFragment를 map_container에 동적으로 추가
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as? com.google.android.gms.maps.SupportMapFragment
            ?: com.google.android.gms.maps.SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction()
                    .replace(R.id.map_container, it)
                    .commit()
            }

        mapFragment.getMapAsync { googleMap ->
            // 구글맵 객체 사용 가능
            googleMap.uiSettings.isZoomControlsEnabled = true
            // 필요시 현재 위치 등 추가
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 