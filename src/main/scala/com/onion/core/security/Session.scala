package com.onion.core.security

trait Session {
  type ID

  def sessionId: ID
  def user: User
}
