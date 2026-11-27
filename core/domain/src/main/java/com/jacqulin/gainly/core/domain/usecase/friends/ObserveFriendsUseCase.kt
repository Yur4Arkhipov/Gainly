package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.model.friends.FriendData
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    operator fun invoke(query: String): Flow<List<FriendData>> {
        return repository.observeFriends(query)
    }
}
