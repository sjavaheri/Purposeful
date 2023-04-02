import {
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverArrow,
  PopoverCloseButton,
  PopoverHeader,
  PopoverBody,
  Button,
  Card,
  CardBody,
  CardFooter,
  FormControl,
  FormLabel,
  Input,
  Textarea,
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon } from "@chakra-ui/icons";
import { useRef } from "react";
import fetchWrapper from "@/utils/fetch_wrapper";
import { useBoolean } from "@chakra-ui/react";
import notification from "@/utils/notification";

export default function CollaborationRequestIncoming({ request }) {
  const additionalContact = request.additionalContact;
  const message = request.message;
  const requestId = request.id;
  const hasResponse = request.hasResponse;

  if (hasResponse) {
    return null;
  }

  const acceptMessage = useRef();
  const acceptContact = useRef();
  const declineMessage = useRef();

  const [buttonLoading, setButtonLoading] = useBoolean(false);

  const handleAccept = async () => {
    const message = acceptMessage.current.value;
    const additionalInfo = acceptContact.current.value;
    console.log("Request id: " + requestId);
    console.log("accept: " + message + " " + additionalInfo);

    setButtonLoading.on();

    const response = await fetchWrapper(
      "/api/collaborationResponse/approve/",
      null,
      "POST",
      {
        collaborationRequestId: requestId,
        message: message,
        additionalContact: additionalInfo,
      }
    );

    if (!response.ok) {
      notification("error", response.errorMessages);
    } else {
      notification("success", "Message sent!");
    }

    setButtonLoading.off();
  };

  const handleIgnore = async () => {
    const message = declineMessage.current.value;
    console.log("Request id: " + requestId);
    console.log("ignore " + message);

    setButtonLoading.on();

    const response = await fetchWrapper(
      "/api/collaborationResponse/decline/",
      null,
      "POST",
      {
        collaborationRequestId: requestId,
        message: message,
      }
    );

    if (!response.ok) {
      notification("error", response.errorMessages);
    } else {
      notification("success", "Message sent!");
    }

    setButtonLoading.off();
  };

  return (
    <Card direction="row" mb="20px">
      <CardBody>
        <b>Message</b> {message} <br />
        <b>Contact Information</b> {additionalContact}
      </CardBody>
      <CardFooter>
        <Popover>
          <PopoverTrigger>
            <Button mr="20px" colorScheme="red" variant="outline">
              Decline
            </Button>
          </PopoverTrigger>
          <PopoverContent w="lg">
            <PopoverArrow />
            <PopoverCloseButton />
            <PopoverHeader>Decline Collaboration Request</PopoverHeader>
            <PopoverBody>
              <FormControl mb="20px">
                <FormLabel>Message</FormLabel>
                <Textarea
                  placeholder="Type your message here..."
                  type="text"
                  rows="5"
                  ref={declineMessage}
                />
              </FormControl>
              <Button
                rightIcon={<CloseIcon />}
                colorScheme="red"
                variant="outline"
                onClick={handleIgnore}
                display="block"
                m="auto"
                isLoading={buttonLoading}
                loadingText="Sending..."
              >
                Decline
              </Button>
            </PopoverBody>
          </PopoverContent>
        </Popover>
        <Popover>
          <PopoverTrigger>
            <Button colorScheme="green" variant="outline">
              Accept
            </Button>
          </PopoverTrigger>
          <PopoverContent w="lg">
            <PopoverArrow />
            <PopoverCloseButton />
            <PopoverHeader>Accept Collaboration Request</PopoverHeader>
            <PopoverBody>
              <FormControl mb="20px">
                <FormLabel>Message</FormLabel>
                <Textarea
                  placeholder="Type your message here..."
                  type="text"
                  rows="5"
                  ref={acceptMessage}
                />
              </FormControl>
              <FormControl mb="20px">
                <FormLabel>Additional Contact</FormLabel>
                <Input
                  placeholder="Add a phone number or social media handle"
                  type="text"
                  ref={acceptContact}
                ></Input>
              </FormControl>
              <Button
                rightIcon={<CheckIcon />}
                colorScheme="green"
                variant="outline"
                onClick={handleAccept}
                display="block"
                m="auto"
                isLoading={buttonLoading}
                loadingText="Sending..."
              >
                Accept
              </Button>
            </PopoverBody>
          </PopoverContent>
        </Popover>
      </CardFooter>
    </Card>
  );
}
