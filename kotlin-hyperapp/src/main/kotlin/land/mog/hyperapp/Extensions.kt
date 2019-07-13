package land.mog.hyperapp

fun Map<*, *>.toJsObject() = jsObject {
    this@toJsObject.forEach {
        this[it.key] = it.value
    }
}

fun jsObject(init: dynamic.() -> Unit): dynamic {
    val obj = js("{}")
    init(obj)
    return obj
}
