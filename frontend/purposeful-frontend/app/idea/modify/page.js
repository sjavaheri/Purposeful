// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import {
  Button,
  Flex,
  Heading,
  Link,
  Stack,
  Text,
  useColorMode,
  useColorModeValue,
} from "@chakra-ui/react";
import ModifyIdea from "@/components/ModifyIdea";
import { useSearchParams } from "next/navigation";

export default function ModifyIdeaPage() {
  const { colorMode, toggleColorMode } = useColorMode(); // TODO: Move the light/dark mode toggle button to the navigation header
  const searchParams = useSearchParams();
  const ideaId = searchParams.get("ideaId");
  console.log("idea id " + ideaId);

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
    <ModifyIdea ideaId={ideaId}/>
    </Flex>
  );
}