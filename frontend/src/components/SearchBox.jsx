import { useState } from "react";

export default function SearchBox({
  mode,
  setMode,

  productName,
  setProductName,

  lotNumber,
  setLotNumber,

  imageFile,
  setImageFile,

  onSearch,
}) {
  const [toolOpen, setToolOpen] = useState(false);

  const handleSearch = () => {
    onSearch();
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      onSearch();
    }
  };

  return (
    <div className="search-box">
      <div className="search-box__input-wrap">
        <button
          type="button"
          className="search-box__prefix"
          onClick={() => setToolOpen((prev) => !prev)}
        >
          +
        </button>

        {toolOpen && (
          <div className="search-box__menu">
            <button
              onClick={() => {
                setMode("PRODUCT");

                setProductName("");

                setLotNumber("");

                setImageFile(null);

                setToolOpen(false);
              }}
            >
              제품명 입력
            </button>

            <button
              onClick={() => {
                setMode("LOT");

                setProductName("");

                setLotNumber("");

                setImageFile(null);

                setToolOpen(false);
              }}
            >
              배치번호 입력
            </button>

            <button
              onClick={() => {
                setMode("IMAGE");

                setProductName("");

                setLotNumber("");

                setImageFile(null);

                setToolOpen(false);
              }}
            >
              이미지로 조회
            </button>
          </div>
        )}

        {mode === "PRODUCT" && (
          <input
            className="search-box__input"
            value={productName ?? ""}
            onChange={(e) => setProductName(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="제품명을 입력해주세요"
          />
        )}

        {mode === "LOT" && (
          <input
            className="search-box__input"
            value={lotNumber ?? ""}
            onChange={(e) => setLotNumber(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="배치번호를 입력해주세요"
          />
        )}

        {mode === "IMAGE" && (
          <input
            className="search-box__input"
            type="file"
            accept="image/*"
            onChange={(e) => setImageFile(e.target.files?.[0] || null)}
          />
        )}
      </div>

      <button className="search-box__btn" onClick={handleSearch}>
        조회
      </button>
    </div>
  );
}
