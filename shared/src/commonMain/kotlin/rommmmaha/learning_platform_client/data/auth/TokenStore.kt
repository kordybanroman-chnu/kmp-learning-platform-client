package rommmmaha.learning_platform_client.data.auth

import eu.anifantakis.lib.ksafe.KSafe

interface TokenStore {
  fun save(session: AuthSession)
  fun current(): AuthSession?
  fun clear()
}

class InMemoryTokenStore : TokenStore {
  private var session: AuthSession? = null

  override fun save(session: AuthSession) {
    this.session = session
  }

  override fun current(): AuthSession? = session

  override fun clear() {
    session = null
  }
}

class KSafeTokenStore(private val ksafe: KSafe) : TokenStore {
  override fun save(session: AuthSession) {
    ksafe.putDirect(KEY, session)
  }

  override fun current(): AuthSession? = ksafe.getDirect<AuthSession?>(KEY, null)

  override fun clear() {
    ksafe.deleteDirect(KEY)
  }

  private companion object {
    const val KEY = "auth_session"
  }
}
