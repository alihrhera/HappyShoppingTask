package com.taskapp.happyshoppingapp.data.enums
/**
 * To determine the correctness of the data for the entry process
 *  [CredentialStatus.VALID] main the data is good to Send
 *
 *  [CredentialStatus.INVALID] main the data isn't good to send
 *
 *  [CredentialStatus.WRONG]  Response Error with data
 *
 */

enum class CredentialStatus {
    INVALID,VALID,WRONG
}