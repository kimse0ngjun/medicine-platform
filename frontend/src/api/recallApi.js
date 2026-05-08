const BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const checkRecall = async (lotNumber) => {
  const res = await fetch(`${BASE_URL}/api/v1/recall/check/${lotNumber}`);
  return res.json();
};
