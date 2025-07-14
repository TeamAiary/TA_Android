package jin.contest.ta_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import jin.contest.ta_android.MainActivity
import androidx.lifecycle.Observer
import jin.contest.ta_android.data.model.LogInRequest
import jin.contest.ta_android.data.model.LogInResponse
import jin.contest.ta_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        // 로그인 버튼
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.logIn(email,password)
            viewModel.logInResult.observe(this, Observer { result ->
                result.onSuccess { response ->
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    // 로그인 성공 시 메인 화면으로 이동 등
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                result.onFailure { e ->
                    Toast.makeText(this, "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            })

        }

        // Google 로그인 버튼
        binding.btnGoogleSignIn.setOnClickListener {
            Toast.makeText(this, "Google 로그인 기능은 추후 구현 예정입니다", Toast.LENGTH_SHORT).show()
        }

        // 회원가입
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
} 