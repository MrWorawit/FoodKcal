package com.example.FoodKcal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.FoodKcal.R.*
import com.example.FoodKcal.R.id.*
import com.example.FoodKcal.ml.Engtestmodel
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class MainActivity : AppCompatActivity() {
    //Todo ประกาศตัวแปร
    private var imageButton: ImageButton? = null
    private var   imageButton2: ImageButton? = null

    lateinit var textureView: TextureView
    lateinit var txtLabel: TextView
    lateinit var imageView: ImageView
    lateinit var cameraManager: CameraManager
    lateinit var cameraDevice: CameraDevice
    lateinit var handler: Handler

    lateinit var bitmap: Bitmap
    lateinit var imageProcessor: ImageProcessor
    lateinit var model: Engtestmodel

    lateinit var labels:List<String>
    var colors = listOf<Int>(
        Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.GRAY,
        Color.BLACK,Color.DKGRAY, Color.MAGENTA, Color.YELLOW, Color.RED)
    val paint = Paint()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        //Todo เวลา Implement Code


        imageButton = findViewById(id.imageButton)
        imageButton!!.setOnClickListener {
            var intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        imageButton2 = findViewById(id.imageButton2)
        imageButton2!!.setOnClickListener {
            var intent = Intent(this, info::class.java)
            startActivity(intent)
        }
        textureView = findViewById<TextureView>(id.textureView)
        //imageView = findViewById<ImageView>(R.id.imageView)
        txtLabel = findViewById<TextView>(id.txtLabel)
        //Background Process
        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        labels = FileUtil.loadLabels(this, "labels.txt")
        imageProcessor = ImageProcessor.Builder().add(ResizeOp(300, 300, ResizeOp.ResizeMethod.BILINEAR)).build()
        model = Engtestmodel.newInstance(this)

        textureView.surfaceTextureListener = object :TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {

            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                bitmap = textureView.bitmap!!

                var image = TensorImage.fromBitmap(bitmap)
                image = imageProcessor.process(image)

                val outputs = model.process(image)
                val score = outputs.scoreAsCategoryList.apply {
                    sortByDescending {
                        it.score
                    }
                }
                txtLabel.text = ""+score[0].label

            }
        }
        cameraManager = getSystemService(Context. CAMERA_SERVICE) as CameraManager
    }

    @SuppressLint("MissingPermission")
    private fun openCamera() {
        cameraManager.openCamera(cameraManager.cameraIdList[0], object:CameraDevice.StateCallback(){
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera
                var surfaceTexture = textureView.surfaceTexture
                var surface = Surface(surfaceTexture)
                var captureRequest =
                    cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                captureRequest.addTarget(surface)
                cameraDevice.createCaptureSession(listOf(surface),
                    object: CameraCaptureSession.StateCallback(){
                        override fun onConfigured(session: CameraCaptureSession) {
                            session.setRepeatingRequest(captureRequest.build(), null, null)
                        }

                        override fun onConfigureFailed(session: CameraCaptureSession) {

                        }
                    },handler)
            }

            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }
        },handler)
    }


    //Todo ถ้าต้องสร้าง function (fun)
    private fun grantPermission () {
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED){
            requestPermissions( arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

    override fun onRequestPermissionsResult (
        requestCode: Int ,
        permissions: Array< out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            grantPermission()
        }
    }

}