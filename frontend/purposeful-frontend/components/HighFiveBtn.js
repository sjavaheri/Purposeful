import { Button } from "@chakra-ui/react";
import { FaHandPaper } from "react-icons/fa";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "@/utils/notification";
import React from "react";

export async function react(idea_id, reactionType) {
  try {
    const payload = {
      reactionType: reactionType,
      idea_id: idea_id,
      email: JSON.parse(localStorage.getItem("appUser")).email,
    };

    let response = null;

    response = await fetchWrapper("/api/reaction", null, "POST", payload);

    let body = await response.json();

    if (!response.ok) {
      notification("error", response.errorMessages);
    }

    return body.date != null;
  } catch (err) {
    console.error("Something went wrong. Unable to react to idea. " + err);
    return false;
  }
}

export default function HighFiveBtn({ idea_id, hasReacted, setHasReacted }) {
  const [color, setColor] = React.useState(hasReacted ? "blue" : "gray");
  const changeColor = async () => {
    const reactResponse = await react(idea_id, "HighFive");
    setHasReacted(reactResponse);
    if (reactResponse) setColor("blue");
    else setColor("gray");
    console.log(reactResponse);
  };
  return (
    <Button
      rightIcon={<FaHandPaper />}
      onClick={() => {
        changeColor();
      }}
      colorScheme={color}
    >
      High Five
    </Button>
  );
}
