// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import CollaborationRequestIncoming from "@/components/CollaborationRequestIncoming";
import { Box } from "@chakra-ui/react";
import fetchWrapper from "@/utils/fetch_wrapper";
import { useEffect, useState } from "react";

export default function CollaborationRequestsPage() {
  const ideaId = 1;

  const [requests, setRequests] = useState([]);

  useEffect(() => {
    const fetchRequests = async () => {
      const response = await fetchWrapper(
        "/api/collaborationRequest/" // + ideaId
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
    fetchRequests();
  }, [ideaId]);

  return (
    <Box w="90vw" display="block" m="auto" pt="20px">
      {requests.map((request) => (
        <CollaborationRequestIncoming request={request} />
      ))}
    </Box>
    // <div style={{ width: "90vw", display: "block", margin: "auto" }}>
    //     Messages for idea {ideaId}
    //   <CollaborationRequestIncoming request={request} />
    // </div>
  );
}
