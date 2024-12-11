//  Copyright (c) 2024 by Pensa Systems, Inc. -- All rights reserved
//  Confidential and Proprietary
package com.pensasystems.shelfxpert

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pensasystems.shelfxpert.databinding.ActivityMainBinding
import com.pensasystems.shelfxpert.databinding.DialogGlobalstoreBinding
import com.pensasystems.pensasdk.PensaSdk


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.c1.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.displaySearchStore(this, onError = {
                    runOnUiThread {
                        Toast.makeText(this, "Error occured when searching stores", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        binding.c2.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.monitorUploads(this, onError = {
                    runOnUiThread {
                        Toast.makeText(this, "Error occured when monitoring uploads", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        binding.c3.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.displayRecentlyVisitedStores(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity, "Error occured when launching recent visits screen.", Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c4.setOnClickListener {
            if (!shouldInitPensa()) {
                val dialogBinding = DialogGlobalstoreBinding.inflate(layoutInflater)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Enter Store Details")
                    .setView(dialogBinding.root)
                    .setPositiveButton("Submit") { _, _ ->
                        val globalStoreId = dialogBinding.globalStoreIdEditText.text?.toString()
                        val sectionKey = dialogBinding.categoryIdEditText.text?.toString()
                        val guid = dialogBinding.guidEditText.text?.toString()
                        if (globalStoreId?.isNotEmpty() == true) {
                            PensaSdk.displayStoreById(
                                this,
                                sectionKey,
                                guid,
                                globalStoreId,
                                onError = {
                                    runOnUiThread {
                                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                                    }
                                })
                        } else {
                            Toast.makeText(this, "GlobalStoreId cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 99)

    }

    private fun shouldInitPensa(): Boolean {
        if (!PensaSdk.isPensaStarted()) {
            Toast.makeText(this, "PensaSdk is not started.", Toast.LENGTH_LONG).show()
            return true
        }

        return false
    }
}
