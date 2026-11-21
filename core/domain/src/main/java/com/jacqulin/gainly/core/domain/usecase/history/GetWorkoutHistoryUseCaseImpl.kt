//package com.jacqulin.gainly.feature.history.data.usecase

//class GetWorkoutHistoryUseCaseImpl @Inject constructor(
//    private val workoutRepository: WorkoutRepository,
//    private val tokenStorage: TokenStorage
//) : GetWorkoutHistoryUseCase {
//
//    override suspend fun invoke(
//        from: String,
//        to: String,
//        last: Int
//    ): Result<List<WorkoutListItem>, WorkoutError> {
//        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
//            ?: return Result.Error(
//                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
//            )
//
//        return workoutRepository.getWorkoutHistory(
//            accessToken = "Bearer $accessToken",
//            from = from,
//            to = to,
//            last = last
//        )
//    }
//}