"use client";
import { Text } from "@chakra-ui/react";

export default function page() {
  if (typeof window !== 'undefined' && !localStorage.getItem("token")) {
    window.location.href = "/login";
  }
  return <Text fontSize="6xl">Home Page</Text>;
}
