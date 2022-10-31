export const useLangCheck = (text) => {
  const pattern = /[^a-z|ㄱ-ㅎ|가-힣]/gi;

  for (let i = 0; i < text.length; i++) {
    if (pattern.test(text.charAt(i))) {
      return false;
    }
  }

  return true;
};
