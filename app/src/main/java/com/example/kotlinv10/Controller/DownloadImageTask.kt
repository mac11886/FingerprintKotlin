//package com.example.kotlinv10.Controller
//
//private class DownloadImageTask(var bmImage: ImageView, var status: Boolean) :
//    AsyncTask<String?, Void?, Bitmap?>() {
//    override fun onPostExecute(result: Bitmap?) {
//        if(status) {
//            bmImage.setImageBitmap(result?.let { getRoundedCornerImage(it) })
//        }else{
//            bmImage.setImageBitmap(result)
//        }
//
//    }
//
//    override fun doInBackground(vararg urls: String?): Bitmap? {
//        val urldisplay = urls[0]
//        var mIcon11: Bitmap? = null
//        try {
//            val `in`: InputStream = URL(urldisplay).openStream()
//            mIcon11 = BitmapFactory.decodeStream(`in`)
//        } catch (e: Exception) {
//            Log.e("Error", e.message)
//            e.printStackTrace()
//        }
//        return mIcon11
//    }
//
//    fun getRoundedCornerImage(bitmap: Bitmap): Bitmap? {
//        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(output)
//        val color = -0xbdbdbe
//        val paint = Paint()
//        val rect = Rect(0, 0, bitmap.width, bitmap.height)
//        val rectF = RectF(rect)
//        val roundPx = (bitmap.width / 2).toFloat()
//        paint.isAntiAlias = true
//        canvas.drawARGB(0, 0, 0, 0)
//        paint.color = color
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        canvas.drawBitmap(bitmap, rect, rect, paint)
//        return output
//    }
//}