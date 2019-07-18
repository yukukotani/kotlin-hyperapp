package land.mog.hyperapp

external interface VNode : VNodeChild

external interface VNodeChild
fun VNodeChild.isString() = asDynamic() is String

interface State
operator fun State.set(key: String, value: Any) {
    asDynamic()[key] = value
}

interface Actions
operator fun Actions.set(key: String, action: () -> Unit) {
    asDynamic()[key] = action
}

typealias View<S, A> = (S, A) -> VNode
