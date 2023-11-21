package vectors

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

inline fun Vec2.toOffset(): Offset = Offset(this.x.toFloat(), this.y.toFloat())

inline fun Vec2.toSize(): Size = Size(this.x.toFloat(), this.y.toFloat())
