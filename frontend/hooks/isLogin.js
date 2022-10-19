import { isValid } from "./isValid";

export const isLogin = () => {
  if (isValid()) {
    return true;
  }

  return false;
};
