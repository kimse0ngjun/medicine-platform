import { useState } from "react";

export default function SearchBox({
  mode,
  setMode,
  lotNumber,
  setLotNumber,
  imageFile,
  setImageFile,
  onSearch,
}) {
  const [toolOpen, setToolOpen] = useState(false);

  const handleSearch = () => {
    onSearch({ mode, lotNumber, imageFile });
  };

  return (
    <div className="search-box">
      <div className="search-box__input-wrap">
        <button
          type="button"
          className="search-box__prefix"
          onClick={() => setToolOpen((p) => !p)}
        >
          +
        </button>

        {toolOpen && (
          <div className="search-box__menu">
            <button
              onClick={() => {
                setMode("LOT");
                setLotNumber("");
                setImageFile(null);
                setToolOpen(false);
              }}
            >
              배치번호 조회
            </button>

            <button
              onClick={() => {
                setMode("IMAGE");
                setLotNumber("");
                setImageFile(null);
                setToolOpen(false);
              }}
            >
              이미지로 조회
            </button>
          </div>
        )}

        {mode === "LOT" ? (
          <input
            className="search-box__input"
            value={lotNumber ?? ""}
            onChange={(e) => setLotNumber(e.target.value)}
            placeholder="배치번호 입력"
          />
        ) : (
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
