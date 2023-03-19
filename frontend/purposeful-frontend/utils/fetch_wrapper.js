// This file contains the fetch wrapper functions

// Backend URL
const BACKEND = "http://127.0.0.1:8080";

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
  const token = localStorage.getItem("token");
  // Check if there is a token
  if (!token) return false;
  let appUserDTO = await fetchWrapper(`${BACKEND}/api/login`);
  // Problem with the request
  if (!appUserDTO) return false;
  // Populate local storage with user info
  localStorage.setItem("email", appUserDTO.email);
  localStorage.setItem("firstname", appUserDTO.firstname);
  localStorage.setItem("lastname", appUserDTO.lastname);
  return true;
}

/**
 * Method to logout a user by clearing local storage.
 */
export function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("email");
  localStorage.removeItem("firstname");
  localStorage.removeItem("lastname");
}

/**
 * Wrapper around fetch api that attaches user token to request.
 *
 * @param {*} endpoint - url
 * @param {*} payload - anything meant to be passed in the body
 * @param {*} headers - headers
 * @param {*} method - ["GET", "POST", "PUT", "DELETE"]
 */
export default async function fetchWrapper(
  endpoint,
  headers = null,
  method = "GET",
  payload = null
) {
  // let json = false;
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
    };
  }

  let options = {
    method,
    ...(payload && method !== "GET" && { body: payload }),
    ...(headers && { headers: headers }),
  };
  try {
    const res = await fetch(endpoint, options);
    const { status, ok } = res;
    if (!ok) {
      console.error(
        `Bad request. Status Code: ${status} Message: ${res.statusText}`
      );
      return;
    }
    // if (json) {
    //   // want json
    //   return await res.json();
    // }
    return res.json();
  } catch (err) {
    console.error(
      `Unable to make ${method} request to ${endpoint} endpoint. ${err}`
    );
    return;
  }
}
