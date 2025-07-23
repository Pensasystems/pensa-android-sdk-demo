//  Copyright (c) 2024 by Pensa Systems, Inc. -- All rights reserved
//  Confidential and Proprietary
package com.pensasystems.shelfxpert

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pensasystems.pensasdk.PensaSdk
import com.pensasystems.shelfxpert.databinding.ActivityMainBinding
import com.pensasystems.shelfxpert.databinding.DialogGlobalstoreBinding
import com.pensasystems.shelfxpert.databinding.DialogScanAreaBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.c1.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.showShelfScans(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Error occured when show shelf scans",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c2.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.showProductScans(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Error occured when show product scans",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c3.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.showStoreSearchView(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error occured when show store search view.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c4.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.showStoresScreen(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error occured when show store screen.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c5.setOnClickListener {
            if (!shouldInitPensa()) {
                val dialogBinding = DialogScanAreaBinding.inflate(layoutInflater)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Enter Scan Area Details")
                    .setView(dialogBinding.root)
                    .setPositiveButton("Submit") { _, _ ->
                        val scanAreaId = dialogBinding.scanAreaIdEditText.text?.toString()
                        if (scanAreaId?.isNotEmpty() == true) {
                            PensaSdk.showScanArea(
                                this,
                                scanAreaId.toInt(),
                                onError = {
                                    runOnUiThread {
                                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                                    }
                                })
                        } else {
                            Toast.makeText(this, "Scan Area Id cannot be empty", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        binding.c6.setOnClickListener {
            if (!shouldInitPensa()) {
                PensaSdk.showStockingScreen(this, onError = {
                    runOnUiThread {
                        Toast.makeText(
                            this@MainActivity,
                            "Error occured when show stocking screen.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

        binding.c7.setOnClickListener {
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
                            PensaSdk.showStoreChecklist(
                                this,
                                globalStoreId,
                                guid,
                                sectionKey,
                                onError = {
                                    runOnUiThread {
                                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                                    }
                                })
                        } else {
                            Toast.makeText(
                                this,
                                "GlobalStoreId cannot be empty",
                                Toast.LENGTH_SHORT
                            ).show()
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
