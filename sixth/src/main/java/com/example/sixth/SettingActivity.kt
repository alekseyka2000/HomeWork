package com.example.sixth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sixth.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    var switch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switch = intent.getBooleanExtra(Key, false)
        binding.storageSwitch.isChecked = switch

        binding.applyButton.setOnClickListener{
            val intentForRes = Intent(this, MainActivity::class.java)
            intentForRes.putExtra("Result", binding.storageSwitch.isChecked)
            setResult(Activity.RESULT_OK, intentForRes)
            finish()
        }
    }

    companion object{
        private const val Key = "ExternalConst"
        @JvmStatic
        fun getIntent(context: Context, extra: Boolean):Intent{
            val intentSetting = Intent(context, SettingActivity::class.java)
            intentSetting.putExtra(Key, extra)
            return intentSetting
        }
    }
}