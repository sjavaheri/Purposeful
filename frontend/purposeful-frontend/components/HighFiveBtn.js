import { IconButton } from '@chakra-ui/react';
import { FaHandPaper } from 'react-icons/fa';
import { react, verifyToken } from "../utils/fetch_wrapper";

export default function HighFiveBtn(idea_id) {
    return (
        <IconButton
            aria-label="High Five"
            icon={<FaHandPaper />}
            onClick={react(idea_id, "HighFive")}
        />
    );
}