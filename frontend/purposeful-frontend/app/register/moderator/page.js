// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import { Flex, Heading, Stack, useColorModeValue } from "@chakra-ui/react";
import Registration from "@/components/Registration";

export default function ModeratorRegistrationPage() {
  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
      <Stack spacing={4} mx={"auto"} maxW={"2xl"} py={12} px={6}>
        <Heading as="h1" size="2xl" align="center">
          Register a new moderator account below
        </Heading>
        <Registration />
      </Stack>
    </Flex>
  );
}
