import { IconButton } from '@chakra-ui/react';
import { FaHandPaper } from 'react-icons/fa';

export default function HighFiveBtn({ onClick }) {
    return (
        <IconButton
            aria-label="High Five"
            icon={<FaHandPaper />}
            onClick={onClick}
        />
    );
}