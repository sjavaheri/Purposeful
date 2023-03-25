import { createStandaloneToast } from "@chakra-ui/react";

/**
 * Provides a notification to the user
 *
 * @param {*} status
 * @param {*} message
 * @param {*} description
 * @author Siger Ma
 */
export default function notification(
  status = "info",
  message = null,
  description = null
) {
  // Possible statuses = ['success', 'error', 'warning', 'info']
  const { toast } = createStandaloneToast();
  toast({
    status: status,
    title: message,
    description: description,
    variant: "subtle",
    position: "top",
    isClosable: true,
  });
}
