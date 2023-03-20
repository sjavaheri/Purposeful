import { IconButton } from '@chakra-ui/react';
import { FaHandPaper } from 'react-icons/fa';

function HighFiveBtn({ onClick }) {
    return (
        <IconButton
            aria-label="High Five"
            icon={<FaHandPaper />}
            onClick={onClick}
        />
    );
}