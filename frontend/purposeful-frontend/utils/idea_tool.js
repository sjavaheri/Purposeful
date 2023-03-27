import fetchWrapper from "./fetch_wrapper";

export async function getDomains(){
    let domains = await fetchWrapper(`/api/domain`);
    let body = await domains.json();
    return body;
  }
  export async function getTopics(){
    let topics = await fetchWrapper(`/api/topic`);
    let body = await topics.json();
    return body;
  }
  export async function getTechs(){
    let techs = await fetchWrapper(`/api/tech`);
    let body = await techs.json();
    return body;
  }