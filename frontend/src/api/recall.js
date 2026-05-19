const BASE_URL = import.meta.env.VITE_API_BASE_URL;

const authHeader = () => ({
  Authorization: `Bearer ${localStorage.getItem("token")}`,
});

export const searchProduct = async (productName) => {
  const res = await fetch(
    `${BASE_URL}/api/v1/recalls/search?productName=${encodeURIComponent(productName)}`,
    {
      headers: authHeader(),
    },
  );

  if (!res.ok) throw new Error("제품 검색 실패");
  return res.json();
};

export const checkRecall = async (lotNumber) => {
  const res = await fetch(
    `${BASE_URL}/api/v1/recalls/lot?lotNumber=${encodeURIComponent(lotNumber)}`,
    {
      headers: authHeader(),
    },
  );

  if (!res.ok) throw new Error("LOT 조회 실패");
  return res.json();
};

export const checkRecallByImage = async (formData) => {
  const res = await fetch(`${BASE_URL}/api/v1/recalls/image`, {
    method: "POST",
    headers: authHeader(),
    body: formData,
  });

  if (!res.ok) throw new Error("이미지 조회 실패");
  return res.json();
};

export const getRecallDetailsByLot = async (lotNumber) => {
  const res = await fetch(
    `${BASE_URL}/api/v1/recalls/detail/lot?lotNumber=${encodeURIComponent(lotNumber)}`,
    {
      headers: authHeader(),
    },
  );

  if (!res.ok) throw new Error("LOT 상세 조회 실패");
  return res.json();
};
