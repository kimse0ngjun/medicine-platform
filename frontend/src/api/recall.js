const BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const searchProduct = async (productName) => {
  const res = await fetch(
    `${BASE_URL}/api/v1/recalls/search?productName=${productName}`,
  );

  if (!res.ok) {
    throw new Error("제품 검색 실패");
  }

  return res.json();
};

export const checkRecall = async (lotNumber) => {
  const res = await fetch(
    `${BASE_URL}/api/v1/recalls/lot?lotNumber=${lotNumber}`,
  );

  if (!res.ok) {
    throw new Error("LOT 조회 실패");
  }

  return res.json();
};

export const checkRecallByImage = async (formData) => {
  const res = await fetch(`${BASE_URL}/api/v1/recalls/image`, {
    method: "POST",
    body: formData,
  });

  if (!res.ok) {
    throw new Error("이미지 조회 실패");
  }

  return res.json();
};
