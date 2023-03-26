// This file contains the fetch wrapper functions

import jwt_decode from "jwt-decode";

// Backend URL
export const BACKEND = "http://127.0.0.1:8080";

export async function react(idea_id, reactionType) {
  try {
    appUserId = JSON.parse(localStorage.getItem("appUser")).id;
    const res = await fetchWrapper("/api/idea/react", {
      method: "POST",
      body: JSON.stringify({ appUserId, idea_id, reactionType }),
    });
    const { status, ok } = await res;
    if (!ok) {
      console.error(
        `Unable to react. Status Code: ${status} Message: ${res.statusText}`
      );
      return false;
    }
    return true;
  }
  catch (err) {
    console.error("Something went wrong. Unable to react to idea. " + err);
    return false;
  }
}

/**
 * Method to login a user given its username and password
 * and store the token in local storage.
 * @param {*} username - username of the user
 * @param {*} password - password of the user, not encoded
 * @returns true if login was successful, false otherwise
 */
export async function login(username, password) {
  const encodedCreds = Buffer.from(`${username}:${password}`).toString(
    "base64"
  );

  try {
    const res = await fetch(`${BACKEND}/api/login`, {
      method: "POST",
      headers: { Authorization: "Basic " + encodedCreds },
    });
    const { status, ok } = await res;
    if (!ok) {
      console.error(
        `Unable to get token. Status Code: ${status} Message: ${res.statusText}`
      );
      return false;
    }
    let token = await res.text();
    // Store token in local storage
    localStorage.setItem("token", token);
    return true;
  } catch (err) {
    console.error("Something went wrong. Unable to get token. " + err);
    return false;
  }
}

/**
 * Method to verify the validity of a token.
 * @returns true if token is valid, false otherwise
 */
export async function verifyToken() {
  localStorage.removeItem("appUser");
  const token = localStorage.getItem("token");
  // Check if there is a token
  if (!token) return false;
  const res = await fetchWrapper("/api/login");
  if (!res.ok) {
    logout();
    return false;
  }
  const appUserDTO = await res.json();
  // Problem with the request
  if (!appUserDTO) return false;
  // Populate local storage with user info
  // Use JSON.parse() to convert string into object in other files
  localStorage.setItem("appUser", JSON.stringify(appUserDTO));
  return true;
}

/**
 * Method to logout a user by clearing local storage.
 */
export function logout() {
  localStorage.removeItem("appUser");
  localStorage.removeItem("token");
}

/**
 * Method to get the grandted authorities of a user.
 * @returns a list of authorities
 * @author Siger Ma
 */
export function getAuthorities() {
  const token = localStorage.getItem("token");
  if (!token) return [];
  const decoded = jwt_decode(token);
  return decoded.grantedAuthorities;
}

/**
 * Wrapper around fetch api that attaches user token to request.
 *
 * @param {*} endpoint - url
 * @param {*} payload - anything meant to be passed in the body
 * @param {*} headers - headers
 * @param {*} method - ["GET", "POST", "PUT", "DELETE"]
 * @returns response object from fetch or error
 */
export default async function fetchWrapper(
  endpoint,
  headers = null,
  method = "GET",
  payload = null
) {
  // Valid HTTP methods
  if (!["GET", "POST", "PUT", "DELETE"].includes(method)) {
    console.error(
      "Invalid method provided. Methods available are: GET, POST, PUT, DELETE"
    );
    return;
  }
  // if (
  //   headers &&
  //   Object.hasOwn(headers, "Content-Type") &&
  //   headers["Content-Type"] == "application/json"
  // ) {
  //   json = true;
  // }
  // Check if there is a token
  let token = localStorage.getItem("token");
  if (token) {
    headers = {
      ...headers,
      Authorization: "Bearer " + token,
      "Content-Type": "application/json",
    };
  } else {
    headers = {
      ...headers,
      "Content-Type": "application/json",
    };
  }

  let options = {
    method,
    ...(payload && method !== "GET" && { body: JSON.stringify(payload) }),
    ...(headers && { headers: headers }),
  };
  try {
    const res = await fetch(BACKEND + endpoint, options);
    const { status, ok } = res;
    if (!ok) {
      const errorMessages = await res.text();
      res.errorMessages = errorMessages;
      console.error(
        `Bad request. Status Code: ${status} Message: ${errorMessages}`
      );
    }
    return res;
  } catch (err) {
    console.error(
      `Unable to make ${method} request to ${endpoint} endpoint. ${err}`
    );
    return err;
  }
}
