package land.mog.hyperapp

external interface VNode : VNodeChild {
    val nodeName: String
    val attributes: dynamic
    val children: Array<VNodeChild>
    val key: VNodeKey
}

external interface VNodeChild
fun VNodeChild.isString() = asDynamic() is String

interface VNodeKey
fun VNodeKey.isString() = asDynamic() is String
fun VNodeKey.isNumber() = asDynamic() is Number
fun VNodeKey.isNull() = asDynamic() == null

interface State
operator fun State.set(key: String, value: Any) {
    asDynamic()[key] = value
}

interface Actions
operator fun Actions.set(key: String, action: () -> Unit) {
    asDynamic()[key] = action
}
