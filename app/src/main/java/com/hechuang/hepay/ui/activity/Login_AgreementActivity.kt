package com.hechuang.hepay.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.hechuang.hepay.R
import com.hechuang.hepay.bean.Temporarydata

class Login_AgreementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login_agreement)
        val agraament_text = findViewById<TextView>(R.id.view_login_agreement)
        agraament_text.text = Temporarydata.login_agreement
        val agraament_bt = findViewById<Button>(R.id.view_login_agrement_bt)
        agraament_bt.setOnClickListener { finish() }
    }
}
