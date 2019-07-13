package land.mog.hyperapp.example

import land.mog.hyperapp.*
import kotlin.browser.document

fun main() {
    val initialState = ExampleState(
        count = 0
    )
    
    val initialActions = ExampleActions(
        up = { value -> { state -> 
            ExampleState(state.count + value)
        } },
        down = { value -> { state ->
            ExampleState(state.count - value)
        } }
    )
        
    val view = { state: ExampleState, actions: ExampleActions -> 
        val upAttributes = jsObject {
            onclick = { actions.up(1) }
        }
        val downAttributes = jsObject { 
            onclick = { actions.down(1) }
        }
        
        h("div", null, arrayOf(
            h("h1", null, arrayOf(state.count)),
            h("button", downAttributes, arrayOf("-")),
            h("button", upAttributes, arrayOf("+"))
        ))
    }
    
    app(initialState, initialActions, view, document.body!!)
}

class ExampleState(
    val count: Int
) : State

class ExampleActions(
    val down: (Int) -> (ExampleState) -> ExampleState,
    val up: (Int) -> (ExampleState) -> ExampleState
) : Actions
