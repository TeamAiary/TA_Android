package jin.contest.ta_android.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private var _binding: FragmentMyPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPageViewModel =
            ViewModelProvider(this).get(MyPageViewModel::class.java)

        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMyPage
        myPageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}