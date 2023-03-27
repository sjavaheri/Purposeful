import { IconButton } from '@chakra-ui/react';
import { FaHandPaper } from 'react-icons/fa';

export default function HighFiveBtn(idea_id) {
    async function react(idea_id, reactionType) {
        try {
            appUserId = JSON.parse(localStorage.getItem("appUser")).id;
            const res = await fetchWrapper("/api/reaction", {
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
    return (
        <IconButton
            aria-label="High Five"
            icon={<FaHandPaper />}
            onClick={() => react(idea_id, "HighFive")}
        />
    );
}