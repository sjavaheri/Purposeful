// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import CollaborationRequestIncoming from "@/components/CollaborationRequestIncoming";
import { Box, Center, Heading, Text } from "@chakra-ui/react";
import fetchWrapper from "@/utils/fetch_wrapper";
import { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";

export default function CollaborationRequestsPage() {
  const searchParams = useSearchParams();
  const ideaId = searchParams.get("ideaId");
  console.log("idea id " + ideaId);

  const [requests, setRequests] = useState([]);
  const [idea, setIdea] = useState({});

  useEffect(() => {
    const fetchRequests = async () => {
      const response = await fetchWrapper(
        "/api/collaborationRequest/" + ideaId
      );

      const handleData = async (response) => {
        if (!response.ok) {
          return null;
        } else {
          return await response.json();
        }
      };

      setRequests(await handleData(response));
    };
    const fetchIdea = async () => {
      const response = await fetchWrapper("/api/idea/" + ideaId);

      const handleData = async (response) => {
        if (!response.ok) {
          return null;
        } else {
          return await response.json();
        }
      };

      setIdea(await handleData(response));
    };
    fetchRequests();
    fetchIdea();
  }, [ideaId]);

  return (
    <>
      <Center>
        <Heading display="block" m="auto" pt="20px">
          Collaboration requests for idea <Text as="i">{idea.title}</Text>
        </Heading>
      </Center>
      <Box w="90vw" display="block" m="auto" pt="20px">
        {requests.map((request) => (
          <CollaborationRequestIncoming request={request} />
        ))}
      </Box>
    </>
  );
}
