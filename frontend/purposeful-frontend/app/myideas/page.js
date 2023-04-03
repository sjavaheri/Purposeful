// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import { Flex, useColorModeValue } from "@chakra-ui/react";
import MyIdeas from "@/components/MyIdeas";

export default function MyIdeasPage() {
  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
      <MyIdeas />
    </Flex>
  );
}
