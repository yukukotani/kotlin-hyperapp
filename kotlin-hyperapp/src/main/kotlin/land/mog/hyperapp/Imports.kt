@file:JsModule("hyperapp")

package land.mog.hyperapp

import org.w3c.dom.Element

external fun h(nodeName: String, attributes: dynamic, children: Array<dynamic>): VNode

external fun <S: State, A: Actions> app(state: S, actions: A, view: View<S, A>, container: Element)
