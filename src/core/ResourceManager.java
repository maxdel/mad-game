package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.model.gameplay.inventory.Item;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class ResourceManager {

    private static ResourceManager instance;

    private final String xmlFilePath = "res/resources.xml";

    private Map<String, AnimationInfo> animationInfos;
    private Map<String, MaskInfo> maskInfos;
    private Map<String, FontInfo> fontInfos;
    private Map<String, ItemInfo> itemInfos;
    private Map<String, Image> imageInfos;

    private ResourceManager() {
        animationInfos = new HashMap<String, AnimationInfo>();
        maskInfos = new HashMap<String, MaskInfo>();
        fontInfos = new HashMap<String, FontInfo>();
        itemInfos = new HashMap<String, ItemInfo>();
        imageInfos = new HashMap<String, Image>();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public void load(GameState gameState) throws SlickException {
        try {
            switch (gameState) {
                case MENUSTART:
                    loadMenuStart();
                    break;
                case GAMEPLAY:
                    loadGamePlay();
                    break;
                case MENUPAUSE:
                    loadMenuPause();
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGamePlay() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement gameplayElement = root.getChildrenByName("gameplay").get(0);

        // gameplay/animations
        XMLElement animationsElement = gameplayElement.getChildrenByName("animations").get(0);
        XMLElementList animationList = animationsElement.getChildrenByName("animation");
        for (int i = 0; i < animationList.size(); ++i) {
            XMLElement animationElement = animationList.get(i);

            String name = animationElement.getAttribute("name");
            String path = animationElement.getAttribute("path");
            boolean isImage = Boolean.valueOf(animationElement.getAttribute("isImage"));

            if (isImage) {
                Image image = new Image(path);
                SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
                Animation animation = new Animation(spriteSheet, 1);

                AnimationInfo animationInfo = new AnimationInfo(animation);
                animationInfos.put(name, animationInfo);
            } else {
                int frameWidth = Integer.valueOf(animationElement.getAttribute("frameWidth"));
                int frameHeight = Integer.valueOf(animationElement.getAttribute("frameHeight"));
                double speedCoef = Double.valueOf(animationElement.getAttribute("speedCoef"));

                SpriteSheet spriteSheet = new SpriteSheet(path, frameWidth, frameHeight);
                Animation animation = new Animation(spriteSheet, 1);

                AnimationInfo animationInfo = new AnimationInfo(animation, speedCoef);
                animationInfos.put(name, animationInfo);
            }
        }

        // gameplay/masks
        XMLElement masksElement = gameplayElement.getChildrenByName("masks").get(0);
        XMLElementList maskList = masksElement.getChildrenByName("mask");
        for (int i = 0; i < maskList.size(); ++i) {
            XMLElement maskElement = maskList.get(i);

            String name = maskElement.getAttribute("name");
            int width = maskElement.getIntAttribute("width");
            int height = maskElement.getIntAttribute("height");
            int radius = maskElement.getIntAttribute("radius");

            maskInfos.put(name, new MaskInfo(width, height, radius));
        }

        // gameplay/items
        XMLElement itemsElement = gameplayElement.getChildrenByName("items").get(0);
        XMLElementList itemList = itemsElement.getChildrenByName("item");
        for (int i = 0; i < itemList.size(); ++i) {
            XMLElement itemElement = itemList.get(i);

            String name = itemElement.getAttribute("name");
            String description = itemElement.getAttribute("description");
            String path = itemElement.getAttribute("path");

            itemInfos.put(name, new ItemInfo(name, description, new Image(path)));
        }

        // gameplay/images
        XMLElement imagesElement = gameplayElement.getChildrenByName("images").get(0);
        XMLElementList imageList = imagesElement.getChildrenByName("image");
        for (int i = 0; i < imageList.size(); ++i) {
            XMLElement imageElement = imageList.get(i);

            String name = imageElement.getAttribute("name");
            String path = imageElement.getAttribute("path");

            imageInfos.put(name, new Image(path));
        }

        in.close();
    }

    private void loadMenuStart() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement manustartElement = root.getChildrenByName("menustart").get(0);

        // menustart/fonts
        XMLElement fontsElement = manustartElement.getChildrenByName("fonts").get(0);
        XMLElementList fontList = fontsElement.getChildrenByName("font");
        for (int i = 0; i < fontList.size(); ++i) {
            XMLElement fontElement = fontList.get(i);

            String name = fontElement.getAttribute("name");
            String fontName = fontElement.getAttribute("fontName");
            int size = fontElement.getIntAttribute("size");
            boolean isBold = fontElement.getBooleanAttribute("isBold");

            fontInfos.put(name, new FontInfo(fontName, size, isBold));
        }

        in.close();
    }

    private void loadMenuPause() throws SlickException, IOException {
        XMLParser xmlParser = new XMLParser();
        InputStream in = new FileInputStream(xmlFilePath);
        XMLElement root = xmlParser.parse("", in);
        XMLElement menupauseElement = root.getChildrenByName("menupause").get(0);

        // menustart/fonts
        XMLElement fontsElement = menupauseElement.getChildrenByName("fonts").get(0);
        XMLElementList fontList = fontsElement.getChildrenByName("font");
        for (int i = 0; i < fontList.size(); ++i) {
            XMLElement fontElement = fontList.get(i);

            String name = fontElement.getAttribute("name");
            String fontName = fontElement.getAttribute("fontName");
            int size = fontElement.getIntAttribute("size");
            boolean isBold = fontElement.getBooleanAttribute("isBold");

            fontInfos.put(name, new FontInfo(fontName, size, isBold));
        }

        in.close();
    }

    public void unload() {
        animationInfos = new HashMap<String, AnimationInfo>();
    }

    public Image getImage(String name) {
        return imageInfos.get(name);
    }

    public Animation getAnimation(String name) {
        return animationInfos.get(name).getAnimation();
    }

    public double getSpeedCoef(String name) {
        return animationInfos.get(name).getSpeedCoef();
    }

    private static class AnimationInfo {

        private Animation animation;
        private double speedCoef;

        public AnimationInfo(Animation animation, double speedCoef) {
            this.animation = animation;
            this.speedCoef = speedCoef;
        }

        public AnimationInfo(Animation animation) {
            this(animation, 0);
        }

        public Animation getAnimation() {
            return animation;
        }

        public double getSpeedCoef() {
            return speedCoef;
        }

    }

    public int getMaskWidth(String name) {
        return maskInfos.get(name).getWidth();
    }

    public int getMaskHeight(String name) {
        return maskInfos.get(name).getHeight();
    }

    public int getMaskRadius(String name) {
        return maskInfos.get(name).getRadius();
    }

    private static class MaskInfo {

        private int width;
        private int height;
        private int radius;

        public MaskInfo(int width, int height, int radius) {
            this.width = width;
            this.height = height;
            this.radius = radius;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getRadius() {
            return radius;
        }

    }

    public String getFontName(String name) {
        return fontInfos.get(name).getFontName();
    }

    public int getFontSize(String name) {
        return fontInfos.get(name).getSize();
    }

    public boolean isFontBold(String name) {
        return fontInfos.get(name).isBold();
    }

    private static class FontInfo {

        private String fontName;
        private int size;
        private boolean isBold;

        public FontInfo(String fontName, int size, boolean isBold) {
            this.fontName = fontName;
            this.size = size;
            this.isBold = isBold;
        }

        public String getFontName() {
            return fontName;
        }

        public int getSize() {
            return size;
        }

        public boolean isBold() {
            return isBold;
        }

    }

    public Image getItemImage(String name) {
        return itemInfos.get(name).getImage();
    }

    public String getItemName(String name) {
        return itemInfos.get(name).getName();
    }

    public String getItemDescription(String name) {
        return itemInfos.get(name).getDescription();
    }

    private static class ItemInfo {

        private Item item;
        private Image image;

        public ItemInfo(String name, String description, Image image) {
            item = new Item(name, description);
            this.image = image;
        }

        public String getName() {
            return item.getName();
        }

        public String getDescription() {
            return item.getDescription();
        }

        public Image getImage() {
            return image;
        }

        public Item getItem() {
            return item;
        }

    }

    public List<Item> getItems() {
        List<ItemInfo> itemInfoList = new ArrayList<ItemInfo>(itemInfos.values());
        List<Item> itemList = new ArrayList<Item>();
        for (ItemInfo itemInfo : itemInfoList) {
            itemList.add(itemInfo.getItem());
        }
        return itemList;
    }

}