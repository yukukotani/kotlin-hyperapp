@file:JsModule("hyperapp")

package land.mog.hyperapp

import org.w3c.dom.Element

external fun h(nodeName: String, attributes: dynamic, children: Array<dynamic>): VNode

external fun app(state: dynamic, actions: dynamic, view: dynamic, container: Element)
