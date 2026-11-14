package com.jacqulin.gainly.feature.history.presentation.ui

//object Reducer {
//    fun reduce(
//        previousState: HistoryViewState,
//        event: HistoryEvent
//    ): Pair<HistoryViewState, HistoryEffect?> {
//        return when (event) {
//            is HistoryEvent.LoadWorkouts -> {
//                previousState.copy(
//                    selectedDate = ,
//                    count = previousState.count + 1) to CounterSideEffect.LogEvent("Incremented to ${previousState.count + 1}")
//            }
//            is CounterEvent.Decrement -> {
//                if (previousState.count > 0) {
//                    previousState.copy(count = previousState.count - 1) to CounterSideEffect.LogEvent("Decremented to ${previousState.count - 1}")
//                } else {
//                    previousState to CounterSideEffect.ShowErrorPopup("Count cannot be negative")
//                }
//            }
//            is CounterEvent.Reset -> {
//                CounterViewState(count = 0) to CounterSideEffect.LogEvent("Counter reset to 0")
//            }
//            is CounterEvent.Error -> {
//                previousState.copy() to CounterSideEffect.ShowErrorPopup(event.message)
//            }
//        }
//    }
//}