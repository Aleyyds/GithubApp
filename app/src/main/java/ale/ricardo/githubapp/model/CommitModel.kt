package ale.ricardo.githubapp.model

data class CommitModel(
        val sha: String,
        val author: Author,
        val commit: Commit,
)

data class Commit(
        val author: AuthorX, val comment_count: Int, val message: String)

data class Author(
        val avatar_url: String,
        val id: Int,
        val login: String,
)

data class AuthorX(
        val date: String, val email: String, val name: String)