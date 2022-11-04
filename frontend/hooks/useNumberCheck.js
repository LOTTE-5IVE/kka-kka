export const useNumberCheck = (text) => {
  const pattern = /^[0-9]+$/;

  for (let i = 0; i < text.length; i++) {
    if (!pattern.test(text.charAt(i))) {
      return false;
    }
  }

  return true;
};
