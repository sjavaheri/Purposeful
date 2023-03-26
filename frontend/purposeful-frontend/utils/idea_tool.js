import fetchWrapper from "./fetch_wrapper";
// Backend URL
const BACKEND = "http://127.0.0.1:8080";

/**
 * 
 * @author Adam Kazma
 */

export async function getDomains(){
    let domains = await fetchWrapper(`${BACKEND}/api/domain`);
    return domains;
  }
  export async function getTopics(){
    let topics = await fetchWrapper(`${BACKEND}/api/topic`);
    return topics;
  }
  export async function getTechs(){
    let techs = await fetchWrapper(`${BACKEND}/api/tech`);
    return techs;
  }