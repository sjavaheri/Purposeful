import { Button } from '@chakra-ui/react';
import { FaHandPaper } from 'react-icons/fa';
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "@/utils/notification";

export default function HighFiveBtn({ idea_id }) {
    async function react(idea_id, reactionType) {
        try {
            const payload = {
                id: null,
                date: null,
                reactionType: reactionType,
                idea_id: idea_id,
                user_id: JSON.parse(localStorage.getItem("appUser")).id
            };
            console.log(payload)

            let response = null;

            response = await fetchWrapper(
                "/api/reaction",
                null,
                "POST",
                payload
            );

            if (!response.ok) {
                notification("error", response.errorMessages);
            } else {
                notification("success", "High Five sent!");
            }
        }
        catch (err) {
            console.error("Something went wrong. Unable to react to idea. " + err);
            return false;
        }
    }
    return (
        <Button rightIcon={<FaHandPaper />} onClick={() => react(idea_id, "HighFive")}>
            High Five
        </Button>
    );
}