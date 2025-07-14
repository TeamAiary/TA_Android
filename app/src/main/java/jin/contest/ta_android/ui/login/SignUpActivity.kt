package jin.contest.ta_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import jin.contest.ta_android.databinding.ActivitySignUpBinding
import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignUpButton()
        observeViewModel()
    }

    private fun setupSignUpButton() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val userName = binding.etUserName.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?: 0
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                binding.rbMale.id -> "MALE"
                binding.rbFemale.id -> "FEMALE"
                else -> ""
            }

            if (email.isBlank() || password.isBlank() || userName.isBlank()) {
                Toast.makeText(this, "이메일, 비밀번호, 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = SignUpRequest(
                email = email,
                password = password,
                userName = userName,
                age = age,
                gender = gender,
                role = "Patient", // 기본값으로 "patient" 설정
                phoneNumber = phoneNumber
            )
            viewModel.signUp(request)
        }
    }

    private fun observeViewModel() {
        viewModel.signUpResult.observe(this, Observer { result ->
            result.onSuccess { response ->
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                // 회원가입 성공 시 로그인 화면으로 이동
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            result.onFailure { e ->
                Toast.makeText(this, "회원가입 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
} 