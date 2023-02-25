package respo.app.nost.data.model

import android.os.Parcel
import android.os.Parcelable

data class News(
     val image: String,
     val title: String,
     val description: String
): Parcelable {
     constructor(parcel: Parcel) : this(
          parcel.readString().toString(),
          parcel.readString().toString(),
          parcel.readString().toString()
     ) {
     }

     override fun describeContents(): Int {
          TODO("Not yet implemented")
     }

     override fun writeToParcel(dest: Parcel, flags: Int) {
          TODO("Not yet implemented")
     }

     companion object CREATOR : Parcelable.Creator<News> {
          override fun createFromParcel(parcel: Parcel): News {
               return News(parcel)
          }

          override fun newArray(size: Int): Array<News?> {
               return arrayOfNulls(size)
          }
     }
}