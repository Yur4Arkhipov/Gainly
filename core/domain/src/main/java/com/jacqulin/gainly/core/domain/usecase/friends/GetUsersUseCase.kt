package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.friends.UserData
import com.jacqulin.gainly.core.domain.model.friends.UsersData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/*class GetUsersUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(query: String): UsersData {
//        val token = tokenStorage.tokens.firstOrNull()?.accessToken
//            ?: return Result.Error(
//                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
//            )
        val token = tokenStorage.tokens.firstOrNull()?.accessToken ?: ""

        return repository.getUsers(token, query)
    }
}*/

class GetUsersUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(nickname: String): UsersData {
        delay(500.milliseconds)

        val users = mockUsers.filter { user ->
            user.username.contains(nickname, ignoreCase = true)
        }

        return UsersData(users = users)
    }

    private val mockUsers = listOf(
        UserData(
            userId = "1",
            username = "alex",
            registrationDate = "12.03.2025"
        ),
        UserData(
            userId = "2",
            username = "maria",
            registrationDate = "18.05.2025",
            isRequestSent = true,
            friendshipId = "request_101"
        ),
        UserData(
            userId = "3",
            username = "john",
            registrationDate = "02.07.2025"
        ),
        UserData(
            userId = "4",
            username = "anna",
            registrationDate = "21.09.2025"
        ),
        UserData(
            userId = "5",
            username = "max",
            registrationDate = "10.11.2025",
            isRequestSent = true,
            friendshipId = "request_102"
        ),
    )
}
