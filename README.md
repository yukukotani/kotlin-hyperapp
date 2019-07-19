# kotlin-hyperapp

[ ![Download](https://api.bintray.com/packages/monchi/maven/kotlin-hyperapp/images/download.svg) ](https://bintray.com/monchi/maven/kotlin-hyperapp/_latestVersion)

[Hyperapp](https://github.com/jorgebucaran/hyperapp) wrapper for Kotlin/JS.

## Installation

```groovy
repositories {
    jcenter()
}

dependencies {
    implementation "land.mog:kotlin-hyperapp:${version}"
}
```

## Usage

First, you can define type of state and actions.

```kotlin
class ExampleState(
    val count: Int
) : State

class ExampleActions(
    val down: (Int) -> (ExampleState) -> ExampleState,
    val up: (Int) -> (ExampleState) -> ExampleState
) : Actions

```

Then, initialize default value and action.

```kotlin
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
```

Finally, construct view and run app.

```kotlin
val view =  { state: ExampleState, actions: ExampleActions ->
    val upAttributes = json(
        "onclick" to { actions.up(1) }
    )
    val downAttributes = json(
        "onclick" to { actions.down(1) }
    )

    h("div", null, arrayOf(
        h("h1", null, arrayOf(state.count)),
        h("button", downAttributes, arrayOf("-")),
        h("button", upAttributes, arrayOf("+"))
    ))
}

app(initialState, initialActions, view, document.body!!)
```

You can also see whole example project [here](https://github.com/Monchi/kotlin-hyperapp/tree/master/example).

## Build

To build, you need simply run this command:

```bash
./gradlew :kotlin-hyperapp:build
```

## License

This project is licensed under the [Apache license 2.0](https://github.com/Monchi/kotlin-hyperapp/blob/master/LICENSE).
