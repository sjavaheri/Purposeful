// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import Registration from "@/components/Registration";
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
import { useState } from "react";

export default function RegularUserRegistrationPage() {
  const { colorMode, toggleColorMode } = useColorMode(); // TODO: Move the light/dark mode toggle button to the navigation header
  const [isLoading, setIsLoading] = useState(false); // TODO: Connect this to the loading state from the API form submission

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
      {/* TODO: Move the light/dark mode toggle button to the navigation header */}
      <Button onClick={toggleColorMode}>
        Toggle {colorMode === "light" ? "Dark" : "Light"}
      </Button>
      <Stack spacing={8} mx={"auto"} maxW={"2xl"} py={12} px={6}>
        <Stack align={"center"}>
          <Heading as="h1" size="2xl">
            Welcome to Purposeful!
          </Heading>
          <Text as="em" fontSize={"3xl"} color={"gray.600"}>
            Share. Create. Purposefully.
          </Text>
        </Stack>
        <Registration />
        <Stack align={"center"}>
          <Button
            isLoading={isLoading}
            loadingText="Submitting"
            size="lg"
            bg={"blue.400"}
            color={"white"}
            _hover={{
              bg: "blue.500",
            }}
            w={"50%"}
          >
            Sign up
          </Button>
          <Text align={"center"}>
            Already a user?{" "}
            <Link color={"blue.400"} href="/login">
              Login
            </Link>
          </Text>
        </Stack>
      </Stack>
    </Flex>
  );
}
